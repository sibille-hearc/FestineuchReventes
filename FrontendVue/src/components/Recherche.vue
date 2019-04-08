<template>
    <div class="container">
        <h1 class="display-5">Recherche d'une offre</h1>
        Résultats pour "{{this.$route.params.keyword}}" :<br><br>
        <div class="row">
            <div class="col-md-4">
                <h2>Filtres</h2>
                <legend>Type de rabais</legend>
                <div>
                    <div class="form-check">
                    <input class="form-check-input" v-on:change="typeRabaisChanged()" type="radio" name="rabaisRadios" id="reduction" value="réduction"  v-model="typeRabais">
                    <label class="form-check-label" for="reduction">
                        Réduction
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" v-on:change="typeRabaisChanged()" type="radio" name="rabaisRadios" id="prixfixe" value="prix fixe"  v-model="typeRabais">
                    <label class="form-check-label" for="prixfixe">
                        Prix fixe
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" v-on:change="typeRabaisChanged()" type="radio" name="rabaisRadios" id="pourcentage" value="pourcentage" v-model="typeRabais">
                    <label class="form-check-label" for="pourcentage">
                        Pourcentage
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" v-on:change="typeRabaisChanged()" type="radio" name="rabaisRadios" id="gratuit" value="gratuit" v-model="typeRabais">
                    <label class="form-check-label" for="gratuit">
                        Gratuit
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" v-on:change="typeRabaisAll()" type="radio" name="rabaisRadios" id="all" value="all" v-model="typeRabais">
                    <label class="form-check-label" for="all">
                        Non filtré par type de rabais
                    </label>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div v-if="filtreRabais===false" class="row offres">
                    <offres v-bind:value = "p" v-bind:index = "i" v-for="(p, i) in offresMatch" v-bind:key="i"></offres>
                </div>
                <div v-else class="row offres">
                    <offres v-bind:value = "p" v-bind:index = "i" v-for="(p, i) in offresMatchRabais" v-bind:key="i"></offres>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Offres from './Offres';
export default {
    name: 'Recherche',
    data() {
        return {
            typeRabais: null,
            filtreRabais: false
        };
    },
    components: {
        'offres': Offres
    },
    computed: {
        offresMatch() {
            if (this.$root.offres != null) {
                let tempOffres = this.$root.offres.filter(offre => (offre.nom.toLowerCase()).indexOf(String(this.$route.params.keyword.toLowerCase())) >= 0 ||
                (offre.entreprise.toLowerCase()).indexOf(String(this.$route.params.keyword.toLowerCase())) >= 0 || (offre.description.toLowerCase()).indexOf(String(this.$route.params.keyword.toLowerCase())) >= 0);
                return tempOffres.filter(offre => (offre.estValidee === '1'));
            }
        },
        offresMatchRabais() {
            if (this.$root.offres != null) {
                return this.$root.offres.filter(offre => (offre.typeRabais.description === this.typeRabais));
            }
        }
    },
    methods: {
        typeRabaisChanged() {
            this.filtreRabais = true;
        },
        typeRabaisAll() {
            this.filtreRabais = false;
        }
    }
};
</script>

<style>

</style>
