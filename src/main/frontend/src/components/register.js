import axios from "axios";
import React from "react";
import {withCookies} from "react-cookie";
import {translate} from "react-i18next";

import User from "../util/User";

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            password: '',
            repeatPassword: '',
            error: undefined,
            passwordsMatch: false
        };

        this.handleUserNameChange = this.handleUserNameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleRepeatPasswordChange = this.handleRepeatPasswordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handlePasswordMatch = this.handlePasswordMatch.bind(this);
        this.cookies = this.props.cookies;
    }

    handleUserNameChange(event) {
        this.setState({userName: event.target.value});
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value});
        this.handlePasswordMatch(event.target.value, this.state.repeatPassword);
    }

    handleRepeatPasswordChange(event) {
        this.setState({repeatPassword: event.target.value});
        this.handlePasswordMatch(this.state.password, event.target.value);
    }

    handlePasswordMatch(password, rPassword) {
        if ((password.length !== 0 && rPassword.length !== 0) && password === rPassword) {
            this.setState({passwordsMatch: true});
        } else {
            this.setState({passwordsMatch: false});
        }
    }

    handleSubmit (event) {
        event.preventDefault();
        axios.post('/user/register', this.state, {
           validateStatus: (status) => {
               return (status >= 200 && status < 300) || status == 400;
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

                        this.props.history.push("/");
                        break;
                    case 400:
                        this.setState({error: true});
                        break;
                }
            });
    }

    render() {
        let registerComponent = null;
        const {t} = this.props;

        if (User.isNotAuthenticated()) {
            registerComponent = <form onSubmit={this.handleSubmit}
                                      className={this.state.userName.length > 0 ? 'form-horizontal has-success' : 'form-horizontal'}>
                <div className="form-group">
                    <label className="col-sm-2">{t('userName')}</label>
                    <div className="col-sm-4">
                        <input type="text" className="form-control"
                               autoFocus={true}
                               value={this.state.userName}
                               onChange={this.handleUserNameChange}/>
                    </div>
                </div>

                <div className={this.state.passwordsMatch ? 'form-group has-success' : 'form-group has-error'}>
                    <label className="col-sm-2">
                        {t('password')}
                    </label>
                    <div className="col-sm-4">
                        <input type="password" name="password" className="form-control"
                               value={this.state.password}
                               onChange={this.handlePasswordChange}/>
                    </div>
                </div>

                <div className={this.state.passwordsMatch ? 'form-group has-success' : 'form-group has-error'}>
                    <label className="col-sm-2">
                        {t('repeatPassword')}
                    </label>
                    <div className="col-sm-4">
                        <input type="password" name="password" className="form-control"
                               value={this.state.repeatPassword}
                               onChange={this.handleRepeatPasswordChange}/>
                        {
                            !this.state.passwordsMatch &&
                            <span id="helpBlock" className="help-block">{t('passwordsDontMatch')}</span>
                        }
                    </div>
                </div>
                <input type="submit"
                       className="btn btn-success"
                       value={t('registerButtonText')}
                       {...(this.state.passwordsMatch && this.state.userName.length > 0) ? {} : {disabled: 'disabled'}}/>
            </form>

        } else {
            registerComponent = <div className="alert alert-danger" role="alert">{t('alreadyRegister')}</div>
        }

        return (
            <div className="component">
                <div className="page-header">
                    <h1>{t('registerFratcher')}</h1>
                </div>
                {registerComponent}
                <p/>
                { this.state.error &&
                    <div className="alert alert-danger" role="alert">{t('userNameAlreadySet')}</div>
                }
            </div>
        );
    }
}


export default translate()(withCookies(Register));