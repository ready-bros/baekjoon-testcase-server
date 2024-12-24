FROM node:22-alpine AS builder

WORKDIR /app

COPY . .

RUN corepack enable
RUN yarn install
RUN yarn build

FROM node:22-alpine

WORKDIR /app

RUN corepack enable

COPY --from=builder /app/package.json ./
COPY --from=builder /app/yarn.lock ./
COPY --from=builder /app/.pnp.* ./
COPY --from=builder /app/.yarnrc.yml ./
COPY --from=builder /app/.yarn/cache ./.yarn/cache
COPY --from=builder /app/.yarn/unplugged ./.yarn/unplugged
COPY --from=builder /app/.yarn/releases ./.yarn/releases
COPY --from=builder /app/dist ./dist

EXPOSE 3000
CMD ["yarn", "node", "dist/main.js"]
