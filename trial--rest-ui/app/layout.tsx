import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "MPP Rest Demo",
  description: "Demo for MPP Rest app",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        {children}
      </body>
    </html>
  );
}
