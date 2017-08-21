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

        this.deleteMatch = this.deleteMatch.bind(this);
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

    deleteMatch (id) {
        axios.delete(`/api/match/${id}`)
            .then(({data}) => {
            this.componentDidMount();
            });
    }

    renderMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.BOTH_LIKE) === undefined) {
            return <p className="bg-warning warnText">{t('noMatchs')}</p>
        } else {
            return this.state.matchs.get(MatchStatus.BOTH_LIKE).map((match => {
                let component;
                let date;

                if (match.comments.length > 0) {
                    let lastComment = match.comments[match.comments.length -1];
                    date = new Date(lastComment.createdAt).toDateString();

                    component = lastComment.author.email + ': ' +lastComment.text;
                } else {
                    component = t('clickToWriteMessage');
                }

                return (
                    <Link to={`/match/${match.id}`} key={match.id} className="list-group-item">
                        <h4 className="list-group-item-heading"> {match.initUser.id === User.id ? match.matchUser.email : match.initUser.email}</h4>
                        <p className="textEllipsis list-group-item-text">{component}</p> <span className="dateRight badge">{date}</span>
                    </Link>
                );
            }));
        }
    }

    renderOpenMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.LIKE) === undefined) {
            return <p className="bg-warning warnText">{t('noLikeMatch')}</p>
        } else {
            return this.state.matchs.get(MatchStatus.LIKE).map((openMatch => {
                return (
                <a onClick={() => this.deleteMatch(openMatch.id)} className="list-group-item list-group-item-warning" key={openMatch.id}>
                    <h4 className="list-group-item-heading">{openMatch.matchUser.email}</h4>
                    <p className="list-group-item-text">{t('revertMatch')}</p>
                </a>
                );
            }));
        }
    }

    renderDislikeMatchs(t) {
        if (this.state.matchs.length === 0 || this.state.matchs.get(MatchStatus.DISLIKE) === undefined) {
            return <p className="warnText bg-warning">{t('noDislikeMatch')}</p>
        } else {
            return this.state.matchs.get(MatchStatus.DISLIKE).map((dislikeMatch => {
                return (
                <a onClick={() => this.deleteMatch(dislikeMatch.id)} className="list-group-item list-group-item-danger" key={dislikeMatch.id}>
                    <h4 className="list-group-item-heading">{dislikeMatch.matchUser.email}</h4>
                    <p className="list-group-item-text">{t('revertMatch')}</p>
                </a>
                );
            }));
        }
    }

    render() {
        const {t} = this.props;
        let component = null;

        if (User.isAuthenticated()) {
            component = <div className="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div className="panel panel-default">
                    <div className="panel-heading" role="tab" id="headingOne">
                        <h4 className="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                {t('yourMatch')}
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                        <div className="panel-body">
                           <div className="list-group">
                               {this.renderMatchs(t)}
                           </div>
                        </div>
                    </div>
                </div>
                <div className="panel panel-default">
                    <div className="panel-heading" role="tab" id="headingTwo">
                        <h4 className="panel-title">
                            <a className="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                {t('yourOpenMatchs')}
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div className="panel-body">
                            <div className="list-group">
                                {this.renderOpenMatchs(t)}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="panel panel-default">
                    <div className="panel-heading" role="tab" id="headingThree">
                        <h4 className="panel-title">
                            <a className="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                {t('yourDislikeMatchs')}
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div className="panel-body">
                            <div className="list-group">
                                {this.renderDislikeMatchs(t)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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