import React from "react";
import {Link} from "react-router-dom";

import User from "../util/User";


// component communication doesn't work with translate (could not find a solution to this).
// Therefore on this page will be no translation for now
class Navigation extends React.Component {
    updateAuthentication() {
        this.forceUpdate();
    }

    render() {
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
                        <Link to="/" className="navbar-brand">
                            <img className="logoFratcher" alt="Brand" src="/assets/fratcherLogo.png" />
                        </Link>
                    </div>
                    <div id="navbar" className="collapse navbar-collapse">
                        <ul className="nav navbar-nav">
                            <li><Link to="/match/find">finde neue Matchs</Link></li>
                            <li><Link to="/match/list">deine Matchs</Link></li>
                        </ul>
                        <ul className="nav navbar-nav navbar-right">
                            { User.isNotAuthenticated() &&
                            <li><Link to="/">Login</Link></li>
                            }
                            { User.isAuthenticated() &&
                            <li><Link to="/">{User.email}</Link></li>
                            }
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

export default Navigation;