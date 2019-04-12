package bindings;

import java.util.List;

public class Person {

    private String id;
    private int index;
    private String guid;
    private boolean isActive;
    private Name name;
    private String picture;
    private String email;
    private String phone;
    private String address;
    private String company;
    private int age;
    private String eyeColor;
    private String about;
    private String balance;
    private String registered;
    private Double longitude;
    private Double latitude;
    private List<String> tags;
    private String greeting;
    private List<Friend> friends;
    private String favoriteFruit;
    private List<Integer> range;

    public Person() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPictureUrl(String pictureUrl) {
        this.picture = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getFavoriteFruit() {
        return favoriteFruit;
    }

    public void setFavoriteFruit(String favoriteFruit) {
        this.favoriteFruit = favoriteFruit;
    }

    public List<Integer> getRange() {
        return range;
    }

    public void setRange(List<Integer> range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return String.format(
        "Person[id=%s, index=%s, guid=%s, isActive=%s, %s, pictureUrl=%s, email=%s, phone=%s, address=[%s], " +
                "company=%s, age=%s, eyeColor=%s, about=[%s], balance=%s, registered=[%s], longitude=%s, " +
                "latitude=%s, tags=%s, greeting=[%s], friends=%s, favouriteFruit=%s, range=%s]",
        id, index, guid, isActive, name, picture, email, phone, address, company, age, eyeColor, about,
                balance, registered, longitude, latitude, tags, greeting, friends, favoriteFruit, range
        );
    }

    public static class Name {

        private String last;
        private String first;

        public Name() { }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        @Override
        public String toString() {
            return String.format("Name=[last=%s, first=%s]", last, first);
        }
    }

    public static class Friend {

        private int id;
        private String name;

        public Friend() { }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Friend=[id=%s, name=%s]", id, name);
        }
    }
}