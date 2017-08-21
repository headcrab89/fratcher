import axios from "axios";
import React from "react";
import {translate} from "react-i18next";
import {Button, Modal} from "react-bootstrap";

import User from "../util/User";
import MatchStatus from "../util/MatchStatus";

class TextList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            texts: [],
            askChat: false,
            match: ''
        }

        this.askChat = this.askChat.bind(this);
        this.removeText = this.removeText.bind(this);
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

    askChat() {
        this.setState({askChat: true});
    }

    gotoChat() {
        // console.log(this.state.match.id);
        this.props.history.push(`/match/${this.state.match.id}`);
    }

    likeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: MatchStatus.LIKE})
            .then(({data}) => {
                this.setState({match: data});

                if (this.state.match.status === MatchStatus.BOTH_LIKE) {
                    this.askChat();
                } else {
                    this.removeText();
                }
            });
    }

    notLikeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: MatchStatus.DISLIKE})
            .then(({data}) => {
                this.removeText();
            });
    }

    removeText() {
        this.setState({askChat: false});
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

                { this.state.askChat &&
                <Modal.Dialog>
                    <Modal.Header>
                        <Modal.Title>{t('youHaveMatch')}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {t('wantToChat', {name: this.state.texts[0].author.email})}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => {
                            this.removeText()
                        }
                        }>{t('later')}</Button>
                        <Button
                            onClick={() => {
                                this.gotoChat()
                            }}
                            bsStyle="primary">{t('okay')}</Button>
                    </Modal.Footer>
                </Modal.Dialog>
                }
            </div>
        );
    }
}


export default translate()(TextList);