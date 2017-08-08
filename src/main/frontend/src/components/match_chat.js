import React from "react";

class MatchChat extends React.Component {
    render() {
        return (
          <div className="component">
              <h1>Match Chat</h1>
              {this.props.match.params.id}
          </div>
        );
    }
}

export default MatchChat;