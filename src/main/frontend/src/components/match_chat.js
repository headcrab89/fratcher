import React from "react";
import axios from "axios";
import moment from "moment";

import User from "../util/User";
import Address from "../util/Address";
import {translate} from "react-i18next";

class MatchChat extends React.Component {
    constructor(props) {
        super();
        this.state = {
            match: undefined,
            chatPartner: '',
            comment: '',
            socket: new WebSocket(Address.websocket)
        }

        this.handleCommentSubmit = this.handleCommentSubmit.bind(this);
        this.handleCommentChange = this.handleCommentChange.bind(this);
        this.sendMessage = this.sendMessage.bind(this);

        this.state.socket.onopen = () => {
            console.log("Connection established...");
            this.state.socket.send("id: " +User.id);
        };

        this.state.socket.onmessage = (messageEvent) => {
            this.componentWillMount();
        };

        this.state.socket.onerror = (errorEvent) => {
            console.log("Error. Connection closed. " +errorEvent);
        };

        this.state.socket.onclose = (closeEvent) => {
            console.log('Connection closed. Code: ' + closeEvent.code + '; Reason: ' + closeEvent.reason);
        };
    }

    sendMessage() {
        this.state.socket.send("send to: " +this.state.chatPartner.id);
    }

    componentWillMount() {
        axios.get(`/api/match/${this.props.match.params.id}`)
            .then(({data}) => {
                this.setState({match: data});
                this.setState({
                    chatPartner: User.id === this.state.match.initUser.id ?
                        this.state.match.matchUser : this.state.match.initUser
                });
            });
    }

    // Before the component will be unmounted the websocket connection will be closed
    componentWillUnmount() {
        this.state.socket.close();
    }

    handleCommentChange(event) {
        this.setState({comment: event.target.value});
    }

    handleCommentSubmit(event) {
        event.preventDefault();

        if (this.state.comment.trim() === "") {
            return;
        }

        axios.post(`/api/match/${this.props.match.params.id}/comment`,
            {
                text: this.state.comment
            })
            .then((data) => {
                this.sendMessage();
                this.setState({comment: ''});
                this.componentWillMount();
            });
    }

    renderComments(t, match) {
        return match.comments.map((comment => {
            return (
                <div className="media" key={comment.id}>
                    <div className="media-left media-middle">
                        <div className="circle media-object">
                            <span>{comment.author.userName[0]}</span>
                        </div>
                    </div>
                    <div className="media-body">
                        <span className="chatDate dateRight badge">{moment(comment.createdAt).format("D.MM.YY H:mm")}</span>
                        <h4 className="media-heading">{comment.author.userName}</h4>
                        <p className="bg-info warnText">{comment.text}</p>
                    </div>
                </div>
            );

        }));
    }

    render() {
        const {t} = this.props;
        const match = this.state.match;

        if (!match) {
            return <div></div>
        }

        const chatUser = this.state.chatPartner;

        return (
            <div className="component">
                <div className="page-header">
                    <h1>{chatUser.userName} <small>{User.isActive(chatUser.lastActivity) ? t('onlineStatus')
                        : t('lastSeen', {date: moment(chatUser.lastActivity).format("D.MM.YY H:mm")})}</small></h1>
                </div>
                {this.renderComments(t, match)}
                <hr/>
                <form onSubmit={this.handleCommentSubmit}>
                    <div className="form-group">
                        <textarea
                            name="comment"
                            value={this.state.comment}
                            onChange={this.handleCommentChange}
                            className="form-control"
                            autoFocus={true}
                            placeholder={t('writeMessage')}/>
                    </div>
                    <input type="submit" value={t('sendMessage')} className="btn btn-success"/>
                </form>
                <p/>
            </div>
        );
    }
}

export default translate()(MatchChat);