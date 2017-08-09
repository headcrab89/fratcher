import axios from "axios";
import React from "react";
import {Link} from "react-router-dom";

import User from "../util/User";
import MatchStatus from "../util/MatchStatus";

class MatchList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            matchs: []
        }
    }

    // This function is called after a refresh and before render() to initialize its state.
    componentDidMount() {
        if (User.isAuthenticated()) {
            axios.get(`api/user/${User.id}/match`)
                .then(({data}) => {
                    this.setState({
                        matchs:  this.groupBy(data, match => match.matchStatus)
                    })
                });
        }
    }

    // How to groupBy objects in javascript: https://stackoverflow.com/questions/14446511/what-is-the-most-efficient-method-to-groupby-on-a-javascript-array-of-objects
    groupBy(list, keyGetter) {
        const map = new Map();
        list.forEach((item) => {
            const key = keyGetter(item);
            const collection = map.get(key);
            if (!collection) {
                map.set(key, [item]);
            } else {
                collection.push(item);
            }
        });
        return map;
    }

    renderMatchs() {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.BOTH_LIKE) === undefined) {
            return <span>You have currently no matchs</span>
        } else {
            return this.state.matchs.get(MatchStatus.BOTH_LIKE).map((match => {
                return (
                    <Link to={`/match/${match.id}`} key={match.id}>
                        <li>
                            {match.id} {match.initUser.id} {match.initUser.email} {match.matchUser.id} {match.matchUser.email} {match.matchStatus}
                        </li>
                    </Link>
                );
            }));
        }
    }

    renderOpenMatchs() {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.LIKE) === undefined) {
            return <span>You have currently no open matchs</span>
        } else {
            return this.state.matchs.get(MatchStatus.LIKE).map((match => {
                return (
                    <li key={match.id}>
                        {match.id} {match.initUser.id} {match.initUser.email} {match.matchUser.id} {match.matchUser.email} {match.matchStatus}
                    </li>
                );
            }));
        }
    }

    renderDislikeMatchs() {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.DISLIKE) === undefined) {
            return <span>You have currently no users you don't like</span>
        } else {
            return this.state.matchs.get(MatchStatus.DISLIKE).map((match => {
                return (
                    <li key={match.id}>
                        {match.id} {match.initUser.id} {match.initUser.email} {match.matchUser.id} {match.matchUser.email} {match.matchStatus}
                    </li>
                );
            }));
        }
    }

    render() {
        let component = null;

        if (User.isAuthenticated()) {
            component = <ul>
                <li>Match
                    <ul>
                        {this.renderMatchs()}
                    </ul>
                </li>
                <li>currently open Matchs
                    <ul>
                        {this.renderOpenMatchs()}
                    </ul>
                </li>
                <li> User you don't like
                    <ul>
                        {this.renderDislikeMatchs()}
                    </ul>
                </li>
            </ul>
        } else {
            component = <span>
                User has to be logged in
            </span>
        }


        return (
            <div className="component">
                <h1>Matchs</h1>
                {component}
            </div>
        );
    }
}


export default MatchList;