import axios from "axios";
import React from "react";


class TextList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            texts: []
        }
    }

    // This function is called before render() to initialize its state.
    componentWillMount() {
        axios.get('/api/text')
            .then(({data}) => {
                this.setState({
                    texts: data
                })
            });
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
        return (
            <div className="component">
                <h1>Texts</h1>
                <ul>
                    {this.renderTexts()}
                </ul>
            </div>
        );
    }
}


export default TextList;