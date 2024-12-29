import { CodeRunnerResult } from '../util/constant';

export interface RunningLanguageConstructor {
  new (runtime: number, code: string, input: string): RunningLanguage;
}

export interface RunningLanguage {
  runtimeLimit: number;

  runCode(): Promise<CodeRunnerResult>;
  calculateTimeLimit(runtime: number): number;
}
