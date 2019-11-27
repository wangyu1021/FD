import Vue from 'vue'
import Router from 'vue-router'
import login from '../page/login'
import manager from '../page/Manager'
import roleList from '../page/role/roleList'
import managerList from '../page/admin/managerList'
import userList from '../page/user/userList'
import clubList from '../page/club/clubList'
import teamList from '../page/team/teamList'
import teamAudit from '../page/team/teamAudit'
import groupList from '../page/group/groupList'
import pkList from '../page/pk/pkList'
import pkAudit from '../page/pk/pkAudit'
import homePage from '../page/home/homePage'
import serviceLog from "../page/system/serviceLog";
import config from "../page/system/config";
import {Message} from "element-ui";
import updatePassword from "../page/updatePassword";

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/manager',
      name: 'manager',
      component: manager,
      meta: {
        requireAuth: true,
      },
      children: [
        {
          path: '/roleList',
          name: 'roleList',
          component: roleList,
        }, {
          path: '/managerList',
          name: 'managerList',
          component: managerList,
        }, {
          path: '/userList',
          name: 'userList',
          component: userList,
        }, {
          path: '/clubList',
          name: 'clubList',
          component: clubList,
        }, {
          path: '/teamList',
          name: 'teamList',
          component: teamList,
        }, {
          path: '/teamAudit',
          name: 'teamAudit',
          component: teamAudit,
        }, {
          path: '/pkList',
          name: 'pkList',
          component: pkList,
        }, {
          path: '/pkAudit',
          name: 'pkAudit',
          component: pkAudit,
        }, {
          path: '/groupList',
          name: 'groupList',
          component: groupList,
        }, {
          path: '/homePage',
          name: 'homePage',
          component: homePage,
        }, {
          path: '/serviceLog',
          name: 'serviceLog',
          component: serviceLog
        }, {
          path: '/config',
          name: 'config',
          component: config
        }, {
          path: '/updatePassword',
          name: 'updatePassword',
          component: updatePassword
        }
      ]

    }
  ]
})
//没登陆之前拦截请求
router.beforeEach((to, from, next) => {
  let that = this;
  if (to.matched.some(r => r.meta.requireAuth)) {
    if (sessionStorage.getItem("token")) {
      next();
    } else {
      Message.warning({
        message: '请先登录'
      })
      next({
        path: '/'
      })
    }
  } else {
    next();
  }
})

export default router;
