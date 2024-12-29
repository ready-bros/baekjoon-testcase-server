import { Javascript } from '../language/javascript';
import { RunningLanguageConstructor } from '../language/running-language';

export interface CodeRunnerResult {
  output: string;
  runtime: number;
}

export const LANGUAGE_MAP = new Map<string, RunningLanguageConstructor>([
  ['javascript', Javascript],
]);
