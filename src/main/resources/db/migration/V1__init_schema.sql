CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE blog_posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    excerpt VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    cover_image VARCHAR(255),
    cover_image_caption VARCHAR(255),
    author_id BIGINT NOT NULL REFERENCES users(id),
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_blog_post_author FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE blog_post_tags (
    blog_post_id BIGINT NOT NULL,
    tag VARCHAR(50) NOT NULL,
    PRIMARY KEY (blog_post_id, tag),
    CONSTRAINT fk_blog_post_tag FOREIGN KEY (blog_post_id) REFERENCES blog_posts(id) ON DELETE CASCADE
);

CREATE TABLE portfolio_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL,
    content TEXT,
    image VARCHAR(255),
    live_url VARCHAR(255),
    github_url VARCHAR(255),
    challenge TEXT,
    solution TEXT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_portfolio_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE portfolio_item_technologies (
    portfolio_item_id BIGINT NOT NULL,
    technology VARCHAR(50) NOT NULL,
    PRIMARY KEY (portfolio_item_id, technology),
    CONSTRAINT fk_portfolio_technology FOREIGN KEY (portfolio_item_id) REFERENCES portfolio_items(id) ON DELETE CASCADE
);

CREATE TABLE portfolio_item_features (
    portfolio_item_id BIGINT NOT NULL,
    feature TEXT NOT NULL,
    PRIMARY KEY (portfolio_item_id, feature),
    CONSTRAINT fk_portfolio_feature FOREIGN KEY (portfolio_item_id) REFERENCES portfolio_items(id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    blog_post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_comment_blog_post FOREIGN KEY (blog_post_id) REFERENCES blog_posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE file_uploads (
    id BIGSERIAL PRIMARY KEY,
    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    url VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_file_upload_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create indexes for better performance
CREATE INDEX idx_blog_posts_author_id ON blog_posts(author_id);
CREATE INDEX idx_blog_posts_published_at ON blog_posts(published_at);
CREATE INDEX idx_comments_blog_post_id ON comments(blog_post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_portfolio_items_user_id ON portfolio_items(user_id);
CREATE INDEX idx_file_uploads_user_id ON file_uploads(user_id);
