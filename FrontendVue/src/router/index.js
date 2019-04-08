import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/components/Home';
import OffreDetail from '@/components/OffreDetail';
import About from '@/components/About';
import ToutesLesOffres from '@/components/ToutesLesOffres';
import Inscription from '@/components/Inscription';
import ToS from '@/components/ToS';
import Contact from '@/components/Contact';
import Connexion from '@/components/Connexion';
import Recherche from '@/components/Recherche';
import MonCompte from '@/components/MonCompte';
import AjouterOffre from '@/components/AjouterOffre';
import ValiderOffres from '@/components/ValiderOffres';
import VueCookies from 'vue-cookies';
Vue.use(VueCookies);
Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/offres',
            name: 'ToutesLesOffres',
            component: ToutesLesOffres
        },
        {
            path: '/offre/:id',
            name: 'OffreDetail',
            component: OffreDetail
        },
        {
            path: '/about',
            name: 'About',
            component: About
        },
        {
            path: '/connexion',
            name: 'Connexion',
            component: Connexion,
            beforeEnter: (to, from, next) => {
                if (VueCookies.get('user_token') == null) {
                    next();
                } else {
                    next('/');
                }
            }
        },
        {
            path: '/inscription',
            name: 'Inscription',
            component: Inscription,
            beforeEnter: (to, from, next) => {
                if (VueCookies.get('user_token') == null) {
                    next();
                } else {
                    next('/');
                }
            }
        },
        {
            path: '/tos',
            name: 'ToS',
            component: ToS
        },
        {
            path: '/contact',
            name: 'Contact',
            component: Contact
        },
        {
            path: '/recherche/:keyword',
            name: 'Recherche',
            component: Recherche
        },
        {
            path: '/ajouterOffre',
            name: 'AjouterOffre',
            component: AjouterOffre,
            beforeEnter: (to, from, next) => {
                if (VueCookies.get('user_token') != null) {
                    next();
                } else {
                    next('/connexion');
                }
            }
        },
        {
            path: '/validerOffres',
            name: 'ValiderOffres',
            component: ValiderOffres,
            beforeEnter: (to, from, next) => {
                if (VueCookies.get('user_token') != null) {
                    next();
                } else {
                    next('/connexion');
                }
            }
        },
        {
            path: '/monCompte',
            name: 'MonCompte',
            component: MonCompte,
            beforeEnter: (to, from, next) => {
                if (VueCookies.get('user_token') != null) {
                    next();
                } else {
                    next('/connexion');
                }
            }
        }
    ]
});
