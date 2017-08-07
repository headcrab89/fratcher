import axios from "axios";
import React from "react";

import User from "../util/User";

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
                        matchs: data
                    })
                });
        }
    }

    renderMatchs() {
        if (this.state.matchs.length === 0) {
            return <span>You have currently no matchs</span>
        } else {
            return this.state.matchs.map((match => {
                return (
                    <li key={match.id}>
                        {match.id} {match.initUser.id} {match.initUser.email} {match.matchUser.id} {match.matchUser.email} {match.matchStatus}
                    </li>
                );
            }));
        }
    }


    render() {
        let component = null;

        if (User.isAuthenticated()) {
            component = <ul>
                {this.renderMatchs()}
            </ul>
        } else {
            component = <span>
                User has to be logged in
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


export default MatchList;