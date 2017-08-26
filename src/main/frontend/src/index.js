import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";
import {I18nextProvider} from "react-i18next";
import {HashRouter as Router, Link, Route, Switch} from "react-router-dom";

import Authentication from "./components/authentication";
import Navigation from "./components/navigation";
import MatchFind from "./components/match_find";
import MatchList from "./components/match_list";
import MatchChat from "./components/match_chat";
import Register from "./components/register";
import i18n from "./i18n";
import User from "./util/User";

class Root extends React.Component {
    constructor(props) {
        super(props);
        // Force initialization of the object.
        User.isAuthenticated();
        this.updateAuthentication = this.updateAuthentication.bind(this);
    }

    updateAuthentication() {
        this.nav.getWrappedInstance().updateAuthentication();
    }

    render () {
        return (
            <div>
                <Navigation ref={(component) => {
                    this.nav = component;
                }}/>
                <Switch>
                    <Route path="/match/find" component={MatchFind} />
                    <Route path="/match/list" component={MatchList} />
                    <Route path="/match/:id" component={MatchChat} />
                    <Route path="/register" component={Register}/>
                    <Route path="/"
                           render={(props) => (
                               <Authentication {...props} updateAuthentication={this.updateAuthentication}/> )}/>
                </Switch>
            </div>
        );
    }
}

ReactDOM.render(
    <CookiesProvider>
        <I18nextProvider i18n={i18n}>
            <Router>
                <Root />
            </Router>
        </I18nextProvider>
    </CookiesProvider>
    , document.getElementById('root'));


