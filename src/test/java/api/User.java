package api;

public class User {
    public String email;
    public String password;

    public static final class UserBuilder {
        public String email;
        public String password;

        UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.email = this.email;
            user.password = this.password;
            return user;
        }
    }
}
