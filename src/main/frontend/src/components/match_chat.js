import React from "react";
import axios from "axios";

import {translate} from "react-i18next";

class MatchChat extends React.Component {
    constructor(props) {
        super();
        this.state = {
            match: undefined,
            comment: ''
        }

        this.handleCommentSubmit = this.handleCommentSubmit.bind(this);
        this.handleCommentChange = this.handleCommentChange.bind(this);
    }

    componentWillMount() {
        axios.get(`/api/match/${this.props.match.params.id}`)
            .then(({data}) => {
                this.setState({
                    match: data
                });
            });
    }

    handleCommentChange(event) {
        this.setState({comment: event.target.value});
    }

    handleCommentSubmit(event) {
        event.preventDefault();
        axios.post(`/api/match/${this.props.match.params.id}/comment`,
            {
                text: this.state.comment
            })
            .then((data) => {
                this.setState({comment: ''});
                this.componentWillMount();
            });
    }

    renderComments(t, match) {
        return match.comments.map((comment => {
            return (
                <div key={comment.id}>
                    <hr/>
                    <div>{t('author')} {comment.author.email}</div>
                    <div>{t('createdAt')} {new Date(comment.createdAt).toISOString()}</div>
                    {comment.text}
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

        return (
          <div className="component">
              <h1>Match Chat</h1>
              <div>Chat von {match.initUser.email} und {match.matchUser.email}</div>
              {this.renderComments(t, match)}
              <hr/>
              <form onSubmit={this.handleCommentSubmit}>
                  <label>
                      Comment
                      <textarea name="comment" value={this.state.comment} onChange={this.handleCommentChange}/>
                  </label>
                  <input type="submit" value="Submit"/>
              </form>
              <hr/>
              Name: {t('applicationName')}
          </div>
        );
    }
}

export default translate()(MatchChat);