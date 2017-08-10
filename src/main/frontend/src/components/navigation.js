import React from "react";
import {Link} from "react-router-dom";
import {translate} from "react-i18next";

import User from "../util/User";

class Navigation extends React.Component {
    render() {
        const {t} = this.props;

        return (
            <nav className="navbar navbar-inverse navbar-fixed-top">
                <div className="container">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#navbar">
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <Link to="/" className="navbar-brand">fratcher</Link>
                    </div>
                    <div id="navbar" className="collapse navbar-collapse">
                        <ul className="nav navbar-nav">
                            <li><Link to="/match/find">{t('findMatch')}</Link></li>
                            <li><Link to="/match/list">{t('listMatch')}</Link></li>
                            { User.isNotAuthenticated() &&
                                <li><Link to="/">{t('login')}</Link></li>
                            }
                            { User.isAuthenticated() &&
                            <li><Link to="/">{t('preferences')}</Link></li>
                            }
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

export default translate()(Navigation);