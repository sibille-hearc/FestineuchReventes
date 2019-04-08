<template>
    <div class="container">
        <h1>Contact</h1>
        <hr>
        <div id="successMsg" v-if="submitStatus==='OK'" class="alert alert-success" role="alert">
            Merci, votre message a bien été envoyé. Nous vous répondrons au plus vite.
        </div>
        <div id="errorMsg" v-if="submitStatus==='ERROR'" class="alert alert-danger" role="alert">
            Erreur, le message n'a pas été envoyé. Veuillez remplir tous les champs correctement.
        </div>
        <div id="contact-form" v-if="submitStatus!='OK'">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group" :class="{ 'form-group--error': $v.template_params.to_name.error }">
                        <label for="rabaisCHF">Votre email *</label>
                        <input :class="{ 'error': $v.template_params.to_name.invalid }" type="email" class="form-control" v-model="template_params.to_name" placeholder="Votre adresse email">
                        <div class="error" v-if="!$v.template_params.to_name.email">Veuillez entrer un email valide</div>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="form_message">Message *</label>
                        <textarea v-model="template_params.message_html" id="form_message" name="message" class="form-control" placeholder="Votre message *" rows="4" required="required" data-error="send a message."></textarea>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <button type="submit" class="btn btn-primary btn-send" v-on:click="submit()">Envoyer</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { email, required } from 'vuelidate/lib/validators';
Vue.use(Vuelidate);
export default {
    name: 'Contact',
    data() {
        return {
            template_params: {
                to_name: '',
                reply_to: 'nicolas.sibille@he-arc.ch',
                message_html: ''
            },
            MsgSent: '',
            submitStatus: null
        };
    },
    mounted() {
        window.emailjscom.init('user_52bflKRnPVrRT3djVfa4O');
    },
    methods: {
        sendContactMessage() {
            window.emailjscom.send(this.$root.service_id, this.$root.template_id, this.$data.template_params);
        },
        submit() {
            this.$v.$touch();
            if (!this.$v.$error) {
                this.sendContactMessage();
                this.$data.submitStatus = 'OK';
            } else {
                this.$data.submitStatus = 'ERROR';
            }
        }
    },
    validations: {
        template_params: {
            message_html: {
                required
            },
            to_name: {
                email
            }
        }
    }
};
</script>

<style>

</style>
