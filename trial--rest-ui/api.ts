import axios from "axios";
import { Trial } from "./types";

const BASE_URL = "http://localhost:8080/competition/trials";

export async function getTrials(): Promise<Trial[]> {
  const res = await axios.get(BASE_URL);
  return res.data;
}

export async function getTrial(id: number): Promise<Trial> {
  const res = await axios.get(`${BASE_URL}/${id}`);
  return res.data;
}

export async function createTrial(trial: Trial): Promise<Trial> {
  const res = await axios.post(BASE_URL, trial);
  return res.data;
}

export async function updateTrial(id: number, trial: Trial): Promise<void> {
  await axios.put(`${BASE_URL}/${id}`, trial);
}

export async function deleteTrial(id: number): Promise<void> {
  await axios.delete(`${BASE_URL}/${id}`);
}