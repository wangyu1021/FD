package com.fd.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import com.fd.bo.PasswordBo;
import com.fd.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dao.ManagerDao;
import com.fd.pojo.Manager;
import com.fd.service.ManagerService;
import com.fd.util.MD5Encoder;

import javax.annotation.Resource;

@Service
public class ManagerServiceImpl implements ManagerService{
	@Resource
	private ManagerDao managerDao;

	public String login(String loginId, String password) {
		Manager Manager=managerDao.findLoginId(loginId);
		String token=TokenUtil.createTKN();
		if(Manager==null) {
			throw new RuntimeException("该账户不存在");
		}
		if(Manager.getClubId()!=0){
			throw new RuntimeException("该账户不是管理员");
		}
		try {
			if(!MD5Encoder.validPassword(password, Manager.getPassword())){
				throw new RuntimeException("密码错误");
			}
			managerDao.updateToken(token,loginId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("出了一点点小错误");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("出了一点点小错误");
		}
		return token;
	}

	public void updatePassword(PasswordBo passwordBo) {
		Manager Manager=managerDao.findLoginId(passwordBo.getLoginId());
		if(Manager==null) {
			throw new RuntimeException("该账户不存在");
		}
		try {
			if(!MD5Encoder.validPassword(passwordBo.getOldPassword(), Manager.getPassword())) {
				throw new RuntimeException("旧密码错误");
			}
		    else if(MD5Encoder.validPassword(passwordBo.getNewPassword(), Manager.getPassword())){
				throw new RuntimeException("旧密码和新密码不能一样");
			}else {
				String pwd = MD5Encoder.getEncryptedPwd(passwordBo.getNewPassword());
				managerDao.updatePassword(passwordBo.getLoginId(),pwd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} 
	}

	public void addManager(Manager Manager) {
		Manager.setCreateTime(new Date());
		Manager.setLoginId(Manager.getJobNumber());
		try {
			String password=MD5Encoder.getEncryptedPwd(Manager.getPassword());
			Manager.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("出现了一点点小错误");
		}
		managerDao.addManager(Manager);
	}

	public void deleteManagerById(Integer id) {
		Manager Manager = managerDao.findManagerById(id);
		if(Manager==null) {
			throw new RuntimeException("此id不存在");
		}else {
			managerDao.deleteManagerById(id);
		}
		
	}

	public List<Manager> findManager() {
		return managerDao.findManager();
	}

	public void updateManagerById(Manager Manager, Integer id) {
		Manager adm=managerDao.findManagerById(id);
		if(adm==null) {
			throw new RuntimeException("您要修改的用户不存在");
		}
		Manager.setLoginId(Manager.getJobNumber());
		managerDao.updateManagerById(Manager,id);
	}

	public Manager findManagerById(Integer id) {
		Manager manager = managerDao.findManagerById(id);
		if(manager==null) {
			throw new RuntimeException("您要查询的用户不存在");
		}
		return manager;
	}

	public List<Manager> findManagerByJobNumOrName(String name, String jobNumber) {
		List<Manager> list=managerDao.findManagerByJobNumOrName(name,jobNumber);
		if(list.size()==0) {
			throw new RuntimeException("没有这个店长");
		}
		return list;
	}

	public List<Manager> findAdmin() {
		return managerDao.findAdmin();
	}

}
