package com.licenta.classmanager.services;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;

import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Flag;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

public class DataService {
	private LinkBuilder linkBuilder;
	private NetworkWorker net_worker;
	private Context context;
	private int i = 0;

	public DataService(Context context) {
		linkBuilder = new LinkBuilder();
		this.context = context;
		net_worker = new NetworkWorker();
	}
	
	public void addTask(int userid, Task t, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.addTask(userid, t), callback);
	}
	
	public void updateTask(int userid, Task t, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.updateTask(userid, t), callback);
	}
	
	public void addClass(int userid, Lesson l, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.addClass(userid, l), callback);
	}
	
	public void updateClass(Lesson l, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.updateClass(l), callback);
	}
	
	public void deleteClass(int classid, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.deleteClass(classid), callback);
	}
	
	public void getClassDays(int classid, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getClassDays(classid), callback);
	}
	
	public void syncClasses(int user_id, final SyncCallback callback) {
	if (NetworkWorker.checkConnection(context)) {
		
		final ClassesDao dao = new ClassesDao(context);
		ArrayList<Lesson> classes = dao.getClasses();
		final int size = classes.size();
		if (size == 0) {
			succes(1, callback);
		} else {
			for (int i = 0; i < size; i++) {
				Flag flag = classes.get(i).getFlag();
				if (flag != null) {
					switch (flag) {
					case ADDED:
						final Lesson l = classes.get(i);
						addClass(user_id, l, new ServiceCallback(context) {

							@Override
							public void onSuccess(JSONObject result) {
								dao.deleteClass(l);
								l.setId(result.optInt("classid"));
								l.setFlag(Flag.SYNCED);
								dao.putClass(l);
								succes(size, callback);
							}

							@Override
							public void onOffline() {
							}
						});
						break;
					case MODIFIED:
						final Lesson l2 = classes.get(i);
						updateClass(l2, new ServiceCallback(context) {

							@Override
							public void onSuccess(JSONObject result) {
								dao.deleteClass(l2);
								l2.setFlag(Flag.SYNCED);
								dao.putClass(l2);
								succes(size, callback);
							}

							@Override
							public void onOffline() {
							}
						});
						break;
					case DELETED:
						final Lesson l3 = classes.get(i);
						deleteClass(l3.getId(), new ServiceCallback(context) {

							@Override
							public void onSuccess(JSONObject result) {
								dao.deleteClass(l3);
								succes(size, callback);
							}

							@Override
							public void onOffline() {
							}
						});
						break;
					default:
						//classes.get(i).setFlag(Flag.SYNCED);
						//dao.putClass(classes.get(i));
						succes(size, callback);
						break;
					}
				} else {
//					classes.get(i).setFlag(Flag.SYNCED);
//					dao.putClass(classes.get(i));
					succes(size, callback);
				}
			}
		}
	} else {
		callback.onError(null, null);
	}
}

	
	public void getUserClasses(int userid, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getUserClasses(userid), callback);
	}

	public void getUser(int userId, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getUser(userId), callback);
	}
	
	public void login(String email, String password, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.login(email, password), callback);
	}
	
	public void succes(int size, SyncCallback callback) {
		i++;
		if (i == size) {
			callback.onSuccess(null);
		}
	}
}
