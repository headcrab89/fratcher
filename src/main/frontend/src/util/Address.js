class Address {
    constructor() {
        this.setWebsocketAddress();
    }

    setWebsocketAddress () {
        var host = window.location.host;
        var protocol;

        if (host.startsWith('localhost')) {
            protocol = "ws"
        } else {
            protocol = "wss";
        }

        this.websocket =  `${protocol}://${host}/message`;
    }
}

export default (new Address);