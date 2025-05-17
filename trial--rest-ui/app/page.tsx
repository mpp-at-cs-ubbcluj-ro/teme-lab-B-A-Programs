"use client";

import { useEffect, useState } from "react";
import { Trial } from "@/types";
import { getTrials } from "@/api";
import TrialForm from "@/components/TrialForm";
import TrialList from "@/components/TrialList";
import { useWebSocket } from "@/hooks/useWebSocket";

export default function Home() {
  const [trials, setTrials] = useState<Trial[]>([]);

  const fetchTrials = async () => {
    const data = await getTrials();
    setTrials(data);
  };

  useEffect(() => {
    fetchTrials();
  }, []);

  useWebSocket(fetchTrials);

  return (
    <main className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-2xl mx-auto space-y-6">
        <h1 className="text-3xl font-bold">Trial Manager</h1>
        <TrialForm onChange={fetchTrials} />
        <TrialList trials={trials} />
      </div>
    </main>
  );
}
