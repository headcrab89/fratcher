import axios from "axios";
import React from "react";
import {Link} from "react-router-dom";
import {translate} from "react-i18next";

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

    renderMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.BOTH_LIKE) === undefined) {
            return <span>{t('noMatchs')}</span>
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

    renderOpenMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.LIKE) === undefined) {
            return <span>{t('noLikeMatch')}</span>
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

    renderDislikeMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.DISLIKE) === undefined) {
            return <span>{t('noDislikeMatch')}</span>
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
        const {t} = this.props;
        let component = null;

        if (User.isAuthenticated()) {
            component = <ul>
                <li>{t('yourMatch')}
                    <ul>
                        {this.renderMatchs(t)}
                    </ul>
                </li>
                <li>{t('yourOpenMatchs')}
                    <ul>
                        {this.renderOpenMatchs(t)}
                    </ul>
                </li>
                <li>{t('yourDislikeMatchs')}
                    <ul>
                        {this.renderDislikeMatchs(t)}
                    </ul>
                </li>
            </ul>
        } else {
            component = <span>
                {t('loginMatch')}
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


export default translate()(MatchList);