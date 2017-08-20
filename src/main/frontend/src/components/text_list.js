import axios from "axios";
import React from "react";
import {translate} from "react-i18next";

import User from "../util/User";
import MatchStatus from "../util/MatchStatus";

class TextList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            texts: []
        }
    }

    // This function is called after a refresh and before render() to initialize its state.
    componentDidMount() {
        if (User.isAuthenticated()) {
            axios.get('/api/text')
                .then(({data}) => {
                    this.setState({
                        texts: data
                    })
                });
        }
    }

    likeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: MatchStatus.LIKE})
            .then(({data}) => {
                console.log(data);
                this.removeText();
            });
    }

    notLikeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: MatchStatus.DISLIKE})
            .then(({data}) => {
                console.log(data);
                this.removeText();
            });
    }

    removeText() {
        this.state.texts.splice(0, 1);
        this.forceUpdate();
    }

    renderTexts(t) {
        if (this.state.texts.length === 0) {
            return <div className="alert alert-warning" role="alert">{t('noTexts')}</div>
        } else {
            var text = this.state.texts[0];

            return <div className="jumbotron">
                <blockquote>
                    <p>{text.userText}</p>
                    <footer>{text.author.email}</footer>
                </blockquote>
                <div className="centerElement">
                    <button type="button"
                            className="btn btn-danger btn-lg btnMargin"
                            data-toggle="tooltip"
                            data-placement="bottom"
                            title={t('dislikeText')}
                            onClick={() => this.notLikeText(text.author)}>
                        <span className="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
                    </button>
                    <button type="button"
                            className="btn btn-primary btn-lg btnMargin"
                            data-toggle="tooltip"
                            data-placement="bottom"
                            title={t('likeText')}
                            onClick={() => this.likeText(text.author)}>
                        <span className="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        }
    }


    render() {
        let component = null;
        const {t} = this.props;

        if (User.isAuthenticated()) {
            component = <ul>
                {this.renderTexts(t)}
            </ul>
        } else {
            component = <span>
                {t('loginText')}
            </span>
        }


        return (
            <div className="component">
                {component}
            </div>
        );
    }
}


export default translate()(TextList);