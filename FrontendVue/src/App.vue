<template>
    <div id="app">
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
          <router-link tag="a" class="navbar-brand" :to="{ name: 'Home', params: {}}">OffresEtudiantsCH</router-link>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                <router-link tag="li" exact-active-class="active nav-item" :to="{ name: 'Home', params: {}}"><a class="nav-link">Accueil</a></router-link>
                <router-link tag="li" exact-active-class="active nav-item" :to="{ name: 'ToutesLesOffres', params: {}}"><a class="nav-link">Toutes les offres</a></router-link>
                <router-link tag="li" exact-active-class="active nav-item" v-if="isLogged" :to="{ name: 'AjouterOffre', params: {}}"><a class="nav-link">Ajouter une offre</a></router-link>
                <router-link tag="li" exact-active-class="active nav-item" :to="{ name: 'About', params: {}}"><a class="nav-link">A propos</a></router-link>
                <li class="nav-item dropdown" v-if="!isLogged">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Mon compte
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <router-link tag="a" class="dropdown-item" :to="{ name: 'Inscription', params: {}}">Inscription</router-link>
                    <router-link tag="a" class="dropdown-item" :to="{ name: 'Connexion', params: {}}">Connexion</router-link>
                    </div>
                </li>
                <router-link tag="li" v-if="isLogged" exact-active-class="active nav-item" :to="{ name: 'MonCompte', params: {}}"><a class="nav-link">Mon compte</a></router-link>
                <li @click="logout()" v-if="isLogged"><a class="nav-link" href="">Déconnexion</a></li>
            </ul>
            <form class="form-inline my-2 my-lg-0" @submit="submitSearch">
              <input class="form-control mr-sm-2" v-model="searchedKeyword" type="text" placeholder="Rechercher une offre" aria-label="Rechercher une offre">
            </form>
          </div>
        </nav>
    <div>
        <h2>{{this.$route.meta.title}}</h2>
        <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Vue from 'vue';
import VueCookies from 'vue-cookies';
Vue.use(VueCookies);
export default {
    name: 'app',
    data() {
        return {
            searchedKeyword: ''
        };
    },
    methods: {
        submitSearch(e) {
            this.$router.push({ name: 'Recherche', params: { keyword: this.$data.searchedKeyword } });
        },
        logout() {
            this.$cookies.remove('user_token');
            this.$router.push({ name: 'Home', params: { } });
        }
    },
    computed: {
        isLogged() {
            return (VueCookies.get('user_token') != null);
        }
    }
};
</script>

<style>
  #app {
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
