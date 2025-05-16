import { Trial } from "../types";

export default function TrialList({ trials }: { trials: Trial[] }) {
  return (
    <div className="grid gap-2 mt-4">
      {trials.map((t) => (
        <div key={t.id} className="p-3 border rounded bg-gray-50 shadow-sm">
          <p><strong>ID:</strong> {t.id}</p>
          <p><strong>Name:</strong> {t.name}</p>
          <p><strong>Age Group:</strong> {t.ageGroup}</p>
        </div>
      ))}
    </div>
  );
}
