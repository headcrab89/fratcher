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
        this.handleRegisterClick = this.handleRegisterClick.bind(this);
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

    handleRegisterClick() {
        this.props.history.push("/register");
    }

    render() {
        let loginComponent = null;
        const {t} = this.props;
        if (User.isNotAuthenticated()) {
            loginComponent = <form onSubmit={this.handleSubmit} className="form-horizontal">
                <div className="form-group">
                    <label className="col-sm-2">{t('email')}</label>
                    <div className="col-sm-4">
                        <input type="text" className="form-control"
                               autoFocus={true}
                               value={this.state.email}
                               onChange={this.handleEmailChange}/>
                    </div>
                </div>

                <div className="form-group">
                    <label className="col-sm-2">
                        {t('password')}
                    </label>
                    <div className="col-sm-4">
                        <input type="password" name="password" className="form-control"
                               value={this.state.password}
                               onChange={this.handlePasswordChange}/>
                    </div>
                </div>
                <input type="submit" className="btn btn-success" value={t('loginButtonText')}/>
                <button type="button" className="registerBtn btn btn-primary" onClick={this.handleRegisterClick}>{t('registerButtonText')}</button>
            </form>

        } else {
            loginComponent =
                <div>
                    <button type="button" className=" btn btn-danger" onClick={this.handleLogout}>{t('logout')}</button>
                </div>
        }

        return (
            <div className="component">
                <h1>{t('loginHeader')}</h1>
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