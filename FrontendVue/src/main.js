// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-select';
import $ from 'jquery';
import './assets/custom.css';
import axios from 'axios';

window.emailjscom = require('emailjs-com');

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
    el: '#app',
    data() {
        return {
            offres: null,
            service_id: 'default_service',
            template_id: 'template_CAFkvirR'
        };
    },
    router: router,
    template: '<my-app></my-app>',
    components: {
        myApp: App
    },
    methods: {
        jQueryUselessFunction() {
            console.log($);
        },
        getAllOffers() {
            let that = this;
            axios({
                method: 'GET',
                url: process.env.ROOT_PATH + '/offre',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json'
                }
            })
            .then(function (response) {
                that.$root.offres = response.data;
            })
            .catch(function (error) {
                console.log(error);
            }
            );
        }
    },
    beforeMount() {
        this.getAllOffers();
    }
});
