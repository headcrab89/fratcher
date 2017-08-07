import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";
import {HashRouter as Router, Link, Route, Switch} from "react-router-dom";

import Authentication from "./components/authentication";
import TextList from "./components/text_list";
import MatchList from "./components/match_list";

ReactDOM.render(
    <CookiesProvider>
        <Router>
            <div>
                <div className="menu">
                    <Link to="/match/find">find match</Link>
                    <Link to="/match/list">list match</Link>
                    <Link to="/">Login</Link>
                </div>


                <Switch>
                    <Route path="/match/find" component={TextList} />
                    <Route path="/match/list" component={MatchList} />
                    <Route path="/" component={Authentication} />
                </Switch>
            </div>
        </Router>
    </CookiesProvider>,
    document.getElementById('root'));



