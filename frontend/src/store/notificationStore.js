import { create } from "zustand"; export const useNotificationStore=create(set=>({unreadCount:0,increment:()=>set(s=>({unreadCount:s.unreadCount+1})),reset:()=>set({unreadCount:0})}));
