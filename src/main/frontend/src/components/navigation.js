import React from "react";
import {Link} from "react-router-dom";
import {translate} from "react-i18next";

import User from "../util/User";

class Navigation extends React.Component {
    updateAuthentication() {
        this.forceUpdate();
    }

    render() {
        const {t} = this.props;

        return (
            <nav className="navbar navbar-inverse navbar-fixed-top">
                <div className="container">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#navbar">
                        </button>
                        <Link to="/" className="navbar-brand">
                            <img className="logoFratcher" alt="Brand" src="/assets/fratcherLogo.png" />
                        </Link>
                    </div>
                    <div id="navbar" className="collapse navbar-collapse">
                        <ul className="nav navbar-nav">
                            { User.isAuthenticated() &&
                            <li><Link to="/match/find">{t('findMatch')}</Link></li>
                            }

                            { User.isAuthenticated() &&
                            <li><Link to="/match/list">{t('listMatch')}</Link></li>
                            }
                        </ul>
                        <ul className="nav navbar-nav navbar-right">
                            { User.isNotAuthenticated() &&
                            <li><Link to="/">{t('login')}</Link></li>
                            }
                            { User.isAuthenticated() &&
                            <li><Link to="/">{User.userName}</Link></li>
                            }
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

// https://devhub.io/repos/i18next-react-i18next
// Without withRef: true only Translation object would be returned and this.nav.updateAuthentication in index.js would not work
export default translate(['common'], {withRef: true})(Navigation);