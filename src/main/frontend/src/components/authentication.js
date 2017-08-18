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
            error: undefined,
            text: '',
            newText: '',
            textChangeActive: false
        };

        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleUserTextChange = this.handleUserTextChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTextChangeSubmit = this.handleTextChangeSubmit.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.cookies = this.props.cookies;
        this.handleRegisterClick = this.handleRegisterClick.bind(this);
        this.activateTextChange = this.activateTextChange.bind(this);
    }

    handleEmailChange(event) {
        this.setState({email: event.target.value});
    }


    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    activateTextChange(event) {
        this.setState({textChangeActive: (this.state.textChangeActive ? false : true)});
    }

    handleUserTextChange(event) {
        this.setState({newText: event.target.value});
    }

    componentDidMount() {
        if (User.isAuthenticated()) {
            axios.get(`api/user/${User.id}/text`)
                .then(({data}) => {
                    this.setState({
                        text: data
                    })
                });
        }
    }

    handleTextChangeSubmit(event) {
        event.preventDefault();
        axios.post('api/text', {userText: this.state.newText})
            .then(({data}) => {
                this.componentDidMount();
                this.setState({textChangeActive: false});
            });

    }

    handleSubmit(event) {
        event.preventDefault();
        axios.post('/user/login', this.state, {
            // We allow a status code of 401 (unauthorized). Otherwise it is interpreted as an error and we can't
            // check the HTTP status code.
            validateStatus: (status) => {
                return (status >= 200 && status < 300) || status == 401;
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
                    <div className="panel panel-default">
                        <div className="panel-heading">
                            <h3 className="panel-title">{t('yourText')}:</h3>
                        </div>
                        <div className="panel-body">
                            {
                                !this.state.textChangeActive &&
                                this.state.text.userText
                            }
                            {
                                this.state.textChangeActive &&
                                <form onSubmit={this.handleTextChangeSubmit} className="textBoxMargin form-horizontal">
                                    <div className="form-group input-group">
                                        <input type="text"
                                               className="form-control"
                                               autoFocus={true}
                                               placeholder={this.state.text.userText}
                                               value={this.state.newText}
                                               onChange={this.handleUserTextChange}/>
                                        <span className="input-group-btn">
                                            <input className="btn btn-default" type="submit" value={t('save')} />
                                        </span>
                                    </div>
                                </form>
                            }

                            {
                                (!this.state.textChangeActive && this.state.text.length === 0) &&
                                <div className="alert alert-warning" role="alert">{t('noText')}</div>
                            }
                        </div>

                        <div className="panel-footer">
                            <button type="button" className="btn btn-primary" onClick={this.activateTextChange}>
                                <span className="glyphicon glyphicon-pencil" aria-hidden="true" />
                            </button>
                        </div>
                    </div>
                    <p/>
                    <button type="button" className="btn btn-danger" onClick={this.handleLogout}>{t('logout')}</button>
                </div>
        }

        return (
            <div className="component">
                <h1>{User.email ? t('currentUser') +' ' +User.email : t('loginHeader')}</h1>
                <p/>
                {loginComponent}
                <p/>
                { this.state.error &&
                <div className="alert alert-danger" role="alert">{t('wrongLogin')}</div>
                }
            </div>
        );
    }
}

export default translate()(withCookies(Authentication));