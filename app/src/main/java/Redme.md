# 📱 Carnet de Dettes Numérique - Boutique de Quartier

## 📌 Présentation du projet
Ce projet consiste à développer une application mobile Android permettant à un boutiquier de gérer efficacement les dettes et remboursements de ses clients.  
L’objectif est de remplacer le carnet papier traditionnel afin de réduire les erreurs, éviter les pertes d’informations et améliorer le suivi des crédits.

Les données sont centralisées dans une base de données en ligne accessible via Internet.

---

## 👥 Membres du groupe
- **Nom Prénom :** DJIGMEDE Marcelin
- **Nom Prénom :** SAWADOGO Sakalo

---

## 🎯 Objectifs
- Digitaliser la gestion des dettes dans une boutique de quartier.
- Assurer une meilleure traçabilité des crédits et remboursements.
- Permettre un accès sécurisé aux données.
- Centraliser les informations sur une base distante.

---

## ⚙️ Fonctionnalités principales

### 🔐 Authentification
- Connexion du boutiquier avec mail et mot de passe.

### 👤 Gestion des clients
- Ajouter un client
- Modifier un client
- Supprimer un client
- Lister les clients
- Informations enregistrées :
    - Nom
    - Numéro de téléphone
    - Adresse (optionnelle)

### 💳 Gestion des dettes
Pour chaque client :
- Enregistrer une dette contenant :
    - Produit / description
    - Montant
    - Date de la dette

### 💰 Gestion des paiements
- Enregistrer un remboursement partiel ou total
- Mise à jour automatique du solde restant

### 📊 Tableau de bord
- Affichage de la liste des clients avec leur solde total
- Mise en évidence des clients les plus endettés

### 🕒 Historique
- Visualisation de l’historique des dettes et paiements par client

---

## ⭐ Fonctionnalités bonus (optionnelles)
- Recherche d’un client (nom ou numéro)
- Filtrage des clients selon le montant de dette
---

## 🏗️ Architecture technique


### 📌 Technologies utilisées
- **Langage :** Kotlin 2.2.21
- **Plateforme :** Android
- **Base de données :** Supabase (PostgreSQL)
- **API :** Supabase REST API (sécurisée)

### 📌 Architecture logicielle
- Architecture basée sur **MVVM (Model - View - ViewModel)** afin de séparer :
    - la logique métier,
    - l’interface utilisateur,
    - la gestion des données.

### 📌 Communication réseau
- Les échanges entre l’application et la base distante se font via des requêtes HTTP.
- Utilisation d’un client réseau Ktor
---

## 🗄️ Base de données distante (Supabase)

### Tables principales proposées
- **boutique** : gestion des identifiants boutiquier
- **client** : informations des clients
- **dette** : dettes enregistrées
- **payment** : paiements effectués


## 🛠️ Installation du projet

### 1️⃣ Cloner le dépôt GitHub
```bash
https://github.com/Ikemga/Detty.git
```

# Ouvrire Android Studio
- Lancer Android Studio
- Sélectionner Open Project
- Choisir le dossier cloné


# Tests de l’application

Scénario de test recommandé

- 2. Se connecter avec un compte boutiquier
- 3. Ajouter un client
- 4. Ajouter une dette pour ce client
- 5. Effectuer un remboursement partiel
- 6. Vérifier la mise à jour du solde
- 7. Consulter l’historique du client
- 8. Vérifier le tableau de bord

# NB : Vous pouvez utiliser ces identifiants pour vous connecter:

- mail : sawadogosakalo@gmail.com
- mot de passe : 123456