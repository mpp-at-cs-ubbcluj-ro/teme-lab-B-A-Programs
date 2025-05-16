"use client";

import { useState } from "react";
import { AgeGroup, Trial } from "../types";
import { createTrial, updateTrial, deleteTrial } from "../api";

const ageGroups: AgeGroup[] = ["SIXEIGHT", "NINEELEVEN", "TWELVEFIFTEEN"];

export default function TrialForm({ onChange }: { onChange: () => void }) {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [ageGroup, setAgeGroup] = useState<AgeGroup>("SIXEIGHT");

  const clearForm = () => {
    setId("");
    setName("");
    setAgeGroup("SIXEIGHT");
  };

  const handleCreate = async () => {
    await createTrial({ name, ageGroup });
    clearForm();
    onChange();
  };

  const handleUpdate = async () => {
    const trialId = parseInt(id);
    if (!trialId) return;
    await updateTrial(trialId, { name, ageGroup });
    onChange();
  };

  const handleDelete = async () => {
    const trialId = parseInt(id);
    if (!trialId) return;
    await deleteTrial(trialId);
    clearForm();
    onChange();
  };

  return (
    <div className="p-4 border rounded-xl shadow bg-white space-y-4">
      <input
        type="number"
        placeholder="ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
        className="input"
      />
      <input
        type="text"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        className="input"
      />
      <select
        value={ageGroup}
        onChange={(e) => setAgeGroup(e.target.value as AgeGroup)}
        className="input"
      >
        {ageGroups.map((group) => (
          <option key={group} value={group}>
            {group}
          </option>
        ))}
      </select>
      <div className="flex gap-2">
        <button className="btn bg-blue-500" onClick={handleCreate}>Add</button>
        <button className="btn bg-yellow-500" onClick={handleUpdate}>Update</button>
        <button className="btn bg-red-500" onClick={handleDelete}>Delete</button>
      </div>
    </div>
  );
}
