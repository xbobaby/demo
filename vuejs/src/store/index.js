import Vue from 'vue'
import Vuex from 'vuex'
import '../lib/sockjs'
import '../lib/stomp'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      token:'',
      name: '',
      userface: '',
      username: '',
      roles: ''
    },
    routes: []
  },
  mutations: {
    initMenu(state, menus){
      state.routes = menus;
    },
    login(state, user){
      state.user = user;
      console.info('store.login:'+JSON.stringify(state.user))
      //window.localStorage.setItem('user', JSON.stringify(user));
    },
    logout(state){
      //window.localStorage.removeItem('user');
      state.routes = [];
    }
  },
  actions: {

  }
});
