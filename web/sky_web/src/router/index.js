import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  //路由模式
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: () => import('../views/Login.vue') },
    { path: '/register', component: () => import('../views/Register.vue') },

    {
      path: '/merchant',
      component: () => import('../views/Merchants/Manager.vue'),
      meta: { role: 'merchant' },
      redirect: '/merchant/home',
      children: [
        { path: 'home',meta:{title:'首页'}, component: () => import('../views/Merchants/Home.vue') },
        { path: 'menu',meta:{title:'菜单'}, component: () => import('../views/Merchants/Menu.vue') },
        { path: 'emp', meta: { title: '员工' }, component: () => import('../views/Merchants/Emp.vue') },
        { path: 'order',meta:{title:'订单'}, component: () => import('../views/Merchants/Order.vue') },
        { path: 'data',meta:{title:'数据'}, component: () => import('../views/Merchants/Data.vue') },
      ]
    },
    {
      path: '/user',
      component: () => import('../views/Users/Manager.vue'),
      meta: { role: 'user' },
      redirect: '/user/home',
      children: [
        { path: 'home', component: () => import('../views/Users/Home.vue') },
        { path: 'user_menu', component: () => import('../views/Users/User_menu.vue') },
        { path: 'user_order', component: () => import('../views/Users/User_order.vue') },
        { path: 'Person', component: () => import('../views/Users/Person.vue') },
      ]
    },
    { path: '/text', component: () => import('../views/Text.vue') },
    { path: '/404', component: () => import('../views/404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' }
  ],
})

export default router

