import axios from "axios";
import React from "react";

import User from "../util/User";

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
        axios.post('/api/match', {matchUser: user, matchStatus: "LIKE"})
            .then(({data}) => {
                console.log(data);
            });
    }

    notLikeText(user) {
        axios.post('/api/match', {matchUser: user, matchStatus: "DISLIKE"})
            .then(({data}) => {
                console.log(data);
            });
    }

    renderTexts() {
        return this.state.texts.map((text => {
            return (
                <li key={text.id}>
                    {text.id} {text.userText} {text.author.email}
                    <span onClick={() => this.likeText(text.author)}>LIKE</span>
                    <span onClick={() => this.notLikeText(text.author)}>DON'T LIKE</span>
                </li>
            );
        }));
    }


    render() {
        let component = null;

        if (User.isAuthenticated()) {
            component = <ul>
                    {this.renderTexts()}
                </ul>
        } else {
            component = <span>
                User has to be logged in
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


export default TextList;