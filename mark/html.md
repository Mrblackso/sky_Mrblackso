## 中间填充 划分为 左右两块

```html
<div style="flex:1;"></div>
```

### bug 1  

如果父级设置了align-items: center;display: flex; 

但是 子级没有居中，查看父级有没有长宽的设置



### bug 2 el-mune 样式加载失败

没有在 main.js 添加 el-plus 组件

```vue
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

app.use(ElementPlus)
```

### bug 3 点击 菜单没有反应

路由设置错误 路径全设成一样的了

路由的路径和 index.js 的 路由名字有出路 导致错误

子路由没有重新设置默认重定向

```vue
default-active="/user/home"
```

```vue
redirect: '/merchant/home',
```

```vue
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: () => import('../views/Login.vue') },
    { path: '/register', component: () => import('../views/Register.vue') },

    {
      path: '/merchant',
      component: () => import('../views/Merchants/Manager.vue'),
      meta: { role: 'merchant' },
      redirect: '/merchant/home', // 添加默认重定向
      children: [
        {
          path: 'home',
          meta: { title: '首页' },
          component: () => import('../views/Merchants/Home.vue')
        },
        {
          path: 'menu',  // 修改为不同的路径
          meta: { title: '菜单' },
          component: () => import('../views/Merchants/Menu.vue')
        },
        {
          path: 'employees',  // 修改为不同的路径
          meta: { title: '员工' },
          component: () => import('../views/Merchants/Emp.vue')
        },
        {
          path: 'orders',  // 修改为不同的路径
          meta: { title: '订单' },
          component: () => import('../views/Merchants/Order.vue')
        },
        {
          path: 'data',  // 修改为不同的路径
          meta: { title: '数据' },
          component: () => import('../views/Merchants/Data.vue')
        },
      ]
    },
    {
      path: '/user',
      component: () => import('../views/Users/Manager.vue'),
      meta: { role: 'user' },
      children: [
        { path: 'home', component: () => import('../views/Users/Home.vue') }
      ]
    },
    { path: '/text', component: () => import('../views/Text.vue') },
    { path: '/404', component: () => import('../views/404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' }
  ]
})

export default router
```

## 菜单选择&子菜单选择 颜色

```vue
.el-menu .is-active {
  background-color: #CAEDF3FF !important;
}

.el-menu .is-active > .el-sub-menu__title {
  background-color: #c3d3ef !important;
}
```



