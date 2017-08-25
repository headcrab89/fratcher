import axios from "axios";
import Cookies from "universal-cookie";
import moment from "moment";

class User {
    constructor() {
        this.reset();
        const cookies = new Cookies();
        const auth = cookies.get('auth');
        if (auth) {
            this.setCookieCredentials(auth);
        }
    }

    setCookieCredentials(credentials) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${credentials.token}`;
        this.set(credentials.user);
    }

    set(data) {
        this.userName = data.userName;
        this.id = data.id;
        this.lastActivity = data.lastActivity;
    }

    reset() {
        this.userName = undefined;
        this.id = -1;
        this.lastActivity = undefined;
    }

    isAuthenticated() {
        return this.userName && this.id != -1;
    }

    isNotAuthenticated() {
        return !this.isAuthenticated();
    }

    isActive(date) {
        if (moment(date).add(5, "m").isAfter(moment())) {
            return true;
        } else {
            return false;
        }
    }
}

export default (new User);