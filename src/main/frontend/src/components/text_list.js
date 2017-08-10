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
            });
    }

    notLikeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: MatchStatus.DISLIKE})
            .then(({data}) => {
                console.log(data);
            });
    }

    renderTexts(t) {
        if (this.state.texts.length === 0) {
          return <span>{t('noTexts')}</span>
        } else {
            return this.state.texts.map((text => {
                return (
                    <li key={text.id}>
                        {text.id} {text.userText} {text.author.email}
                        <button onClick={() => this.likeText(text.author)}>{t('likeText')}</button>
                        <button onClick={() => this.notLikeText(text.author)}>{t('dislikeText')}</button>
                    </li>
                );
            }));
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
                <h1>Texts</h1>
                {component}
            </div>
        );
    }
}


export default translate()(TextList);