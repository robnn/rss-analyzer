import { StompConfig, StompService } from '@stomp/ng2-stompjs';


export class WebSocketConfig {


    public static uri: string = 'ws://localhost:8080/ws-endpoint';


    public static topic: string = "/topic/notification";


}






export const stompConfig: StompConfig = {


    url: WebSocketConfig.uri,


    headers: {


    },


    heartbeat_in: 0,


    heartbeat_out: 20000,


    reconnect_delay: 5000,


    debug: false


};