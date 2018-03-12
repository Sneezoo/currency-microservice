import Vue from 'vue'
import Router from 'vue-router'
import Overview from '@/components/Overview'
import Currency from '@/components/Currency'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Overview',
      component: Overview
    },
    {
      path: '/currency/:currency',
      name: 'Currency',
      component: Currency
    }
  ]
})
