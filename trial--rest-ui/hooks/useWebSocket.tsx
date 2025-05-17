import { useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export const useWebSocket = (onMessage: (msg: any) => void) => {
  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe('/trials', (message) => {
          const body = JSON.parse(message.body);
          onMessage(body);
        });
      },
    });

    client.activate();

    return () => {
      client.deactivate();
    };
  }, []);
};