package com.dsv2019.pvt15.prepapp.tipsrelated;

public class JsonTips {

        private int id;

        private String title;

        private String description;

        private boolean warmth;

        private boolean water;

        private boolean shelter;

        private boolean food;

        private boolean health;

        private boolean security;

        private boolean storage;

        private boolean other;

        private int likes;

        private String creator;

        public JsonTips(int id, String title, String description, boolean warmth, boolean water, boolean shelter, boolean food, boolean health, boolean security, boolean storage, boolean other, int likes, String creator) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.warmth = warmth;
            this.water = water;
            this.shelter = shelter;
            this.food = food;
            this.health = health;
            this.security = security;
            this.storage = storage;
            this.other = other;
            this.likes = likes;
            this.creator = creator;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public boolean isWarmth() {
            return warmth;
        }

        public void setWarmth(boolean warmth) {
            this.warmth = warmth;
        }

        public boolean isWater() {
            return water;
        }

        public void setWater(boolean water) {
            this.water = water;
        }

        public boolean isShelter() {
            return shelter;
        }

        public void setShelter(boolean shelter) {
            this.shelter = shelter;
        }

        public boolean isFood() {
            return food;
        }

        public void setFood(boolean food) {
            this.food = food;
        }

        public boolean isHealth() {
            return health;
        }

        public void setHealth(boolean health) {
            this.health = health;
        }

        public boolean isSecurity() {
            return security;
        }

        public void setSecurity(boolean security) {
            this.security = security;
        }

        public boolean isStorage() {
            return storage;
        }

        public void setStorage(boolean storage) {
            this.storage = storage;
        }

        public boolean isOther() {
            return other;
        }

        public void setOther(boolean other) {
            this.other = other;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

    }
