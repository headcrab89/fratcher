import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";
import {I18nextProvider} from "react-i18next";
import {HashRouter as Router, Link, Route, Switch} from "react-router-dom";

import Authentication from "./components/authentication";
import Navigation from "./components/navigation";
import TextList from "./components/text_list";
import MatchList from "./components/match_list";
import MatchChat from "./components/match_chat";
import i18n from "./i18n";
import User from "./util/User";

User.isAuthenticated();

ReactDOM.render(
    <CookiesProvider>
        <I18nextProvider i18n={i18n}>
            <Router>
                <div>
                    <Navigation/>
                    <Switch>
                        <Route path="/match/find" component={TextList} />
                        <Route path="/match/list" component={MatchList} />
                        <Route path="/match/:id" component={MatchChat} />
                        <Route path="/" component={Authentication} />
                    </Switch>
                </div>
            </Router>
        </I18nextProvider>
    </CookiesProvider>,
    document.getElementById('root'));



