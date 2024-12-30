import { CodeRunnerResult } from '../util/constant';

export interface RunningLanguageConstructor {
  new (runtime: number, code: string, input: string): RunningLanguage;
}

export abstract class RunningLanguage {
  runtimeLimit: number;

  abstract runCode(): Promise<CodeRunnerResult>;
  abstract calculateTimeLimit(runtime: number): number;

  protected handleTimeout(reject: any) {
    setTimeout(() => {
      reject(new Error('Timeout'));
    }, this.runtimeLimit);
  }
}
