<template>
    <div class="container">
        <h1 class="display-5">A propos</h1>
        <hr>
        <div class="about">
            <h2>A quoi sert cette application web ?</h2>
            Bonne question ! Notre application web te permet, en tant qu'<strong>étudiant Suisse</strong>, 
            d'avoir accès à <strong>toutes les offres faites pour toi</strong> dans notre pays. 
            Les études coûtent chères, et tu n'as sûrement pas beaucoup de réserve pour les années qu'ils te restent. 
            💸 Alors soit confient : grâce à notre application tu bénéficieras des meilleurs prix 
            avec ta carte étudiante !
            <h2>Mais concrètement ?</h2>
            C'est simple, tu t'inscrits et tu renseignes tes informations personnelles comme l'école 🏫 dans laquelle tu étudies. 
            Tu peux aussi ajouter des cantons de préférences dans ton profil et des catégories d'offres favoris. 
            Puis, il te suffit de laisser notre application t'informer par mail 📧 des bons plans qui correspondent à tes préférences. 
            Et si tu cherches un restaurant 🍝 qui fait des réductions pour les étudiants dans un autre canton, pas de problèmes ! 
            Grâce à la recherche sur la page principal du site, tu trouves tout ce dont tu as besoin, indépendamment de ton profil.
            <h2>Autre chose que je dois savoir ?</h2>
            Nous sommes un groupe d'étudiants de la HE-ARC et nous avons commencé ce projet durant l'un de nos cours pour répondre à un
            besoin que nous avons préalablement analysé. Les offres consultables sur notre site sont toutes ajoutées par des membres inscrits 
            sur OffresEtudiantsCH. Nous faisons tout notre possible pour valider au mieux les offres ajoutées, mais nous ne pouvons en aucun 
            cas garantir la validité de ses offres, ni leur exactitude. Veuillez s'il vous plait vous renseigner auprès de l'entreprise concernée 
            en cas de doute sur une offre. Si vous trouvez une erreur dans les offres listées sur notre site web, vous pouvez 
            <router-link :to="{ name: 'Contact', params: {}}"><a>nous contacter</a></router-link> pour que nous la modifions ou la retirions.
        </div>
    </div> <!-- /container -->
    
</template>

<script>
export default {
    name: 'Home',
    data() {
        return {
        };
    }
};
</script>

<style>
    .about {
         text-align: justify;
    }
</style>
