import React from "react";
import axios from "axios";

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

    renderComments(match) {
        return match.comments.map((comment => {
            return (
                <div key={comment.id}>
                    <hr/>
                    <div>Author {comment.author.email}</div>
                    <div>Created at {new Date(comment.createdAt).toISOString()}</div>
                    {comment.text}
                </div>
            );

            }));
    }

    render() {
        const match = this.state.match;

        if (!match) {
            return <div></div>
        }

        return (
          <div className="component">
              <h1>Match Chat</h1>
              <div>Chat von {match.initUser.email} und {match.matchUser.email}</div>
              {this.renderComments(match)}
              <hr/>
              <form onSubmit={this.handleCommentSubmit}>
                  <label>
                      Comment
                      <textarea name="comment" value={this.state.comment} onChange={this.handleCommentChange}/>
                  </label>
                  <input type="submit" value="Submit"/>
              </form>
          </div>
        );
    }
}

export default MatchChat;