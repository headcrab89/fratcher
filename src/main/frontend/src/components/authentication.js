import axios from "axios";
import React from "react";
import {withCookies} from "react-cookie";
import {translate} from "react-i18next";

import User from "../util/User";

class Authentication extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            error: undefined
        };

        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.cookies = this.props.cookies;
    }

    handleEmailChange(event) {
        this.setState({email: event.target.value});
    }


    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }


    handleSubmit(event) {
        event.preventDefault();
        axios.post('/user/login', this.state, {
            // We allow a status code of 401 (unauthorized). Otherwise it is interpreted as an error and we can't
            // check the HTTP status code.
            validateStatus: (status) => {
                return (status >= 200 && status < 300) || status == 401
            }
        })
            .then(({data, status}) => {
                switch (status) {
                    case 200:
                        User.setCookieCredentials(data);
                        this.setState({error: undefined});

                        // Store authentication values even after refresh.
                        this.cookies.set('auth', {
                            token: data.token,
                            user: User
                        }, {path: '/'});

                        this.props.updateAuthentication();
                        this.props.history.push("/match/find");
                        break;

                    case 401:
                        this.setState({error: true});
                        break;
                }
            });
    }

    handleLogout() {
        axios.defaults.headers.common['Authorization'] = undefined;
        User.reset();
        this.cookies.remove('auth');
        this.forceUpdate();
        this.props.updateAuthentication();
    }


    render() {
        let loginComponent = null;
        const {t} = this.props;
        if (User.isNotAuthenticated()) {
            loginComponent =
                <form onSubmit={this.handleSubmit}>
                    <label>
                        {t('email')}
                        <input type="text" name="email" value={this.state.email} onChange={this.handleEmailChange}/>
                    </label>
                    <label>
                        {t('password')}
                        <input type="password" name="password" value={this.state.password}
                               onChange={this.handlePasswordChange}/>
                    </label>
                    <input type="submit" value={t('submit')}/>
                </form>
        } else {
            loginComponent =
                <div>
                    <button type="button" className="btn btn-danger" onClick={this.handleLogout}>{t('logout')}</button>
                </div>
        }

        return (
            <div className="component">
                <h1>Authentication</h1>
                {t('currentUser')}: {User.email || t('notLogedin')}
                <p/>
                {loginComponent}
                <p/>
                { this.state.error &&
                <div className="error">
                    {t('wrongLogin')}
                </div>
                }
            </div>
        );
    }
}

export default translate()(withCookies(Authentication));